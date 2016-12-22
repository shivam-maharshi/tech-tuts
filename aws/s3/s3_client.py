from boto.s3 import connect_to_region
from boto.s3.key import Key
import boto.s3.connection
import config

class S3Client(object):
    
    def __init__(self, config):
        self.conn = connect_to_region(config.region,
                                      aws_access_key_id=config.access_key,
                                      aws_secret_access_key=config.secret_key,
                                      is_secure=True,
                                      calling_format = boto.s3.connection.OrdinaryCallingFormat())
        if(config.bucket_name):
            self.bucket = self.conn.get_bucket(config.bucket_name)

    def set_bucket(self, bucket_name):
        self.bucket = self.conn.get_bucket(bucket_name)
        
    def get_bucket(self):
        return self.bucket    
        
    def get_bucket_list(self):
        buckets = self.conn.get_all_buckets()
        for bucket in buckets:
            print(bucket.name)
        return self.conn.get_all_buckets()
    
    def create(self, key, data, metadata):
        k = Key(self.bucket)
        for m_key, m_val in metadata.iteritems():
            k.set_metadata(m_key, m_val)
        k.key=key
        k.set_contents_from_string(data)
        print("Created key:: %s with data : %s" % (key, data))
        
    def get(self, key, version_id=None, md_keys=None):
        # Fetches the latest key by default.
        k = self.bucket.get_key(key_name=key, version_id=version_id)
        print("Data for key:: %s is : %s" % (key, k.get_contents_as_string()))
        for md_key in md_keys:
            print("Metadata info:: group = %s" % k.get_metadata(md_key))
        return k.get_contents_as_string()
    
    def get_key_list(self):
        keys = self.bucket.list()
        for key in keys:
            print key.name
        return keys
        
    
s3_client = S3Client(config=config)
# data_file = open('/home/shiv0150/Projects/workspace/boto-tutorial/data/idp.xml')
# data = data_file.read()
# s3_client.get_bucket_list()
# s3_client.create("astra.rackspace.local", 'version 4 data')
# s3_client.create('test_key', 'test_data', {'group':'janus-group'})
s3_client.get('test_key', md_keys=['group'])
# s3_client.get_key_list()
