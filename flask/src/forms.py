from flask_wtf import Form
from wtforms import StringField, BooleanField
from wtforms.validators import DataRequired

class LoginForm(Form):
	openid = StringField('openid', validators=[DataRequired()])
	remember_me = BooleanField('remember_me', default=False)
	
class MetadataForm(Form):
	sp_entity_id = StringField('sp_entity_id', validators=[DataRequired()])
	sp_description = StringField('sp_description')
	sp_assertion_consumer_service_endpoint = StringField('sp_assertion_consumer_service_endpoint')
	sp_single_logout_endpoint = StringField('sp_single_logout_endpoint')
	sp_ebcl = BooleanField('sp_ebcl')
	sp_back_channel_slo_endpoint = StringField('sp_back_channel_slo_endpoint')
	sp_public_key = StringField('sp_public_key')
	name_id_format = StringField('name_id_format')
	assertion_signature = StringField('assertion_signature')
	signature_algorithm = StringField('signature_algorithm')
	digest_algorithm = StringField('digest_algorithm')
	assertion_enryption = StringField('assertion_enryption')
	team_name = StringField('team_name')
	technical_contact_firstname = StringField('technical_contact_firstname')
	technical_contact_surname = StringField('technical_contact_surname')
	technical_contact_email = StringField('technical_contact_email')
	technical_contact_telephone = StringField('technical_contact_telephone')
	support_contact_firstname = StringField('support_contact_firstname')
	support_contact_surname = StringField('support_contact_surname')
	support_contact_email = StringField('support_contact_email')
	support_contact_telephone = StringField('support_contact_telephone')