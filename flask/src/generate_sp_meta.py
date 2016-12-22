'''
Created on Jun 6, 2016

@author: shiv0150
'''

from saml2.config import SPConfig
from saml2.metadata import entity_descriptor, metadata_tostring_fix
from saml2.server import Server
from saml2.validate import valid_instance

class AstraServer(Server):
    def __init__(self, *args, **kwargs):
        super(AstraServer, self).__init__(*args, **kwargs)

    def create_sp_metadata(self):
        nspair = {"xs": "http://www.w3.org/2001/XMLSchema"}
        ed = entity_descriptor(self.config)
        valid_instance(ed)
        return metadata_tostring_fix(ed, nspair)
    
conf = SPConfig()
context = ""
conf.setattr(context, 'cert_file', '/home/shiv0150/Projects/workspace/metadata/data/sso_cert')
conf.setattr(context, 'description', 'SP Janus')
organization = {'url': 'http://www.janus.rackspace.com',
                'display_name': 'Janus',
                'name': 'Janus'}
conf.setattr(context, 'organization', organization)
contact_person = [{'telephone_number': '666911007',
                   'email_address': 'shivam.maharshi@rackspace.com',
                   'sur_name': 'maharshi', 'given_name': 'shivam',
                   'contact_type': 'technical'},
                  {'telephone_number': '666911007',
                   'email_address': 'abc@rackspace.com',
                   'sur_name': 'c',
                   'given_name': 'ab',
                   'contact_type': 'support'}]
conf.setattr(context, 'contact_person', contact_person)
conf.setattr(context, 'name_id_format', 'urn:oasis:names:tc:SAML:2.0:nameid-format:persistent')
conf.setattr(context, 'serves', 'sp')
sp_endpoints = {'single_logout_service':[
                 ('http://astra.rackspace.local/slo/v2', 'urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect'),
                 ('http://astra.rackspace.local/slo/v2', 'urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST')],
                 'assertion_consumer_service': [
                    ('http://astra.rackspace.local/sso/v2', 'urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST')]
                }
conf.setattr(context, '_sp_endpoints', sp_endpoints)
conf.setattr(context, 'entityid', 'http://astra.rackspace.local')
conf.setattr(context, 'encryption_type', 'signing')


#sp = AstraServer(config=conf, stype='sp')
#print(sp.create_sp_metadata())

# TODO: Pretty print XML doc
# Parse cert to find out expiration date.
# Validate certificate.
# Flask Application - Upload
conf.setattr(context, 'valid_for', 168)
