package org.edu.solr.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;

/**
 * A custom search ranking component that gives you the flexibility to perform
 * these operations. 1. Customize ranking function and scores. 2. Perform Query
 * Expansion. 3. Implement Pseudo Relevance Feedback.
 * 
 * @author shivam.maharshi
 */
public class CustomSearchComponent extends SearchComponent {

	private static Logger logger = Logger.getLogger(CustomSearchComponent.class);

	public CustomSearchComponent() {
	}

	@Override
	public void init(NamedList args) {
		super.init(args);
	}

	/**
	 * This method handles the query expansion part which occurs before the
	 * original user query is even run against the index.
	 */
	@Override
	public void prepare(ResponseBuilder rb) throws IOException {
		Set<Term> termSet = new HashSet<>();
		Query originalQuery = rb.getQuery();
		originalQuery.extractTerms(termSet);
		// Tries to expand original query with topic label derived from terms
		String label = "disease";
		if (label != null) {
			TermQuery supplementQuery = new TermQuery(new Term("text", label.toLowerCase()));
			// Not to overwhelm original query
			supplementQuery.setBoost(originalQuery.getBoost() * .9f);
			BooleanQuery query = new BooleanQuery();
			query.add(originalQuery, BooleanClause.Occur.SHOULD);
			query.add(supplementQuery, BooleanClause.Occur.SHOULD);
			originalQuery = query;
		}
		// Adds custom scores as tf-idf + fieldValue1 * weight1 + fieldValue2 *
		Map<String, Float> wieghts = new HashMap<String, Float>();
		wieghts.put("topic", 1.0F);
		rb.setQuery(new ScoreBoostingQuery(originalQuery, wieghts));
	}

	@Override
	public void process(ResponseBuilder rb) throws IOException {
		logger.info("IDEAL Ranking Component process phase invoked.");
	}

	@Override
	public String getDescription() {
		return "Custom search ranking component";
	}

	@Override
	public String getSource() {
		return "https://github.com/shivam-maharshi/SolrJavaTutorial";
	}

	private static final class ScoreBoostingQuery extends CustomScoreQuery {
		private final Map<String, Float> fieldWeights;

		ScoreBoostingQuery(Query subQuery, Map<String, Float> fieldWeights) {
			super(subQuery);
			this.fieldWeights = fieldWeights;
		}

		@Override
		protected CustomScoreProvider getCustomScoreProvider(AtomicReaderContext context) throws IOException {
			return new CustomScoreProvider(context) {
				@Override
				public float customScore(int doc, float subQueryScore, float[] valSrcScores) throws IOException {
					// Original score - tf-idf
					float score = super.customScore(doc, subQueryScore, valSrcScores);
					logger.info(String.format("DocId [ %s ], original score [ %s ]", doc, score));
					Document d = context.reader().document(doc);
					for (String field : fieldWeights.keySet()) {
						IndexableField scoreField = d.getField(field);
						if (scoreField != null && scoreField.numericValue() != null) {
							float scoreBoost = scoreField.numericValue().floatValue();
							float weight = fieldWeights.get(field);
							logger.info(String.format("DocId [ %s ], field [ %s ], score boost [ %s ], weight [ %s ]",
									doc, field, scoreBoost, weight));
							score += weight * scoreBoost;
						}
					}
					logger.info(String.format("DocId [ %s ], final score [ %s ]", doc, score));
					return score;
				}
			};
		}
	}

}
