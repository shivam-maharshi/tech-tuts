'''
Creates mock objects 
'''

from mock import MagicMock, patch

class TestClass:
    
    def method(self):
        print('this is a weird method.')
        return 100;
    

obj = TestClass
obj.method = MagicMock(return_value = 3)
obj.method(3, 4, 5, key='value')
# We have asserted that the method is always called with these
# argunments. In that case return value 3.
obj.method.assert_called_with(3, 4, 5, key='value')


def mock_search(self):
    class MockSearchQuerySet(SearchQuerySet):
        def __iter__(self):
            return iter(["foo", "bar", "baz"])
    return MockSearchQuerySet()

# SearchForm here refers to the imported class reference in myapp,
# not where the SearchForm class itself is imported from
@mock.patch('myapp.SearchForm.search', mock_search)
def test_new_watchlist_activities(self):
    # get_search_results runs a search and iterates over the result
    self.assertEqual(len(myapp.get_search_results(q="fish")), 3)
