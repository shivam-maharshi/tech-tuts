from flask import render_template, flash, redirect, request
from src import app, generate_sp_meta
from forms import LoginForm
from forms import MetadataForm
from saml2.config import SPConfig

@app.route('/')
@app.route('/index')
def index():
    user = {'nickname': 'Miguel'}  # fake user
    posts = [	{ 
            	'author': {'nickname': 'John'}, 
            	'body': 'Beautiful day in Portland!' 	
       			},
        		{ 	
            	'author': {'nickname': 'Susan'}, 
            	'body': 'The Avengers movie was so cool!' 
        		}]
    return render_template('index.html', title='Home', user=user, posts=posts)

@app.route('/login', methods=['GET', 'POST'])
def login():
    form = LoginForm()
    if form.validate_on_submit():
        flash('Login requested for OpenID="%s", remember_me=%s' %(form.openid.data, str(form.remember_me.data)))
        return redirect('/index')
    return render_template('login.html', 
                           title='Sign In',
                           form=form, providers=app.config['OPENID_PROVIDERS'])

@app.route('/astra/tools/sp/metadata', methods=['GET', 'POST'])
def generate_sp_metadata():
    form = MetadataForm()
    if request.method == 'POST' and form.validate_on_submit():
        conf = SPConfig()
        context = ""
        conf.setattr(context, 'description', form.sp_description.data)
        conf.setattr(context, 'entityid', form.sp_entity_id.data)
        conf.setattr(context, 'encryption_type', 'signing')
        sp_endpoints = {'single_logout_service':[
                 (form.sp_single_logout_endpoint.data, 'urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect'),
                 (form.sp_back_channel_slo_endpoint.data, 'urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST')],
                 'assertion_consumer_service': [
                    (form.sp_assertion_consumer_service_endpoint.data, 'urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST')]
                }
        conf.setattr(context, '_sp_endpoints', sp_endpoints)
        conf.setattr(context, 'cert_file', '/home/shiv0150/Projects/workspace/metadata/data/sso_cert')
        organization = {'url': form.sp_entity_id.data,
			            'display_name': form.team_name.data,
			            'name': form.team_name.data}
        conf.setattr(context, 'organization', organization)
        contact_person = [{'telephone_number': form.technical_contact_telephone.data,
				   'email_address': form.technical_contact_email.data,
				   'sur_name': form.technical_contact_surname.data,
				   'given_name': form.technical_contact_firstname.data,
				   'contact_type': 'technical'},
				   {'telephone_number': form.support_contact_telephone.data,
	    		   'email_address': form.support_contact_email.data,
				   'sur_name': form.support_contact_surname.data,
				   'given_name': form.support_contact_firstname.data,
				   'contact_type': 'support'}]
        conf.setattr(context, 'contact_person', contact_person)
        conf.setattr(context, 'serves', 'sp')
        conf.setattr(context, 'name_id_format', 'urn:oasis:names:tc:SAML:2.0:nameid-format:persistent')
        sp = generate_sp_meta.AstraServer(config=conf, stype='sp')
        sp_metadata = sp.create_sp_metadata()
        flash(sp_metadata)
        return redirect('/astra/tools/sp/generated_metadata')
    return render_template('metadata.html', title = 'Faws', form = form);


@app.route('/astra/tools/sp/generated_metadata')
def generated_sp_metadata():
	return render_template('generated_metadata.html');
