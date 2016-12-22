from datetime import datetime

from boto3 import client
from boto3 import Session, s3
from boto3.exceptions import Boto3Error
from botocore.exceptions import ClientError


from astra.config import SP_MD_ACCESS_KEY, SP_MD_SECRET_KEY, SP_MD_S3_BUCKET

class S3Client(object):

    def __init__(self):
        self.using_client()
        #self.using_resource()

    def using_client(self):
        try:
            self.client = client('s3',
                                 aws_access_key_id=SP_MD_ACCESS_KEY,
                                 aws_secret_access_key=SP_MD_SECRET_KEY)
            
            # Check if the bucket exists.
            self.client.head_bucket(Bucket=SP_MD_S3_BUCKET)
            
            # Save a key to bucket.
            # response = self.client.put_object(Bucket=SP_MD_S3_BUCKET,
            #                                  Key='test_more',
            #                                  Body='some_more_text',
            #                                  Metadata={'sp-group': 'RackImpersonation'})
            #print response.get('ResponseMetadata').get('HTTPStatusCode')
            
            # Download a key to a file.
            response = self.client.download_file(SP_MD_S3_BUCKET, 'test_more', '/tmp/hello.txt')
            print response

            # Fetch all keys from a bucket.
            #obj_list = self.client.list_objects(Bucket=SP_MD_S3_BUCKET)
            #keys = obj_list.get('Contents')
            #for key in keys:
            #    print key.get('Key')
            
            # Fetch a specific key from a bucket.
            #key = self.client.get_object(Bucket=SP_MD_S3_BUCKET,
            #                             Key='astra.rackspace.local',
            #                             IfModifiedSince=datetime(2016, 31, 8, 15, 38, 45))
            #print key['Body'].read()
            #print key['Metadata']
                
            # Fetch only metadata of key from a bucket.
            #md = self.client.head_object(Bucket=SP_MD_S3_BUCKET,
            #                        Key='astra.rackspace.local')
            #print md.get('Metadata')
            
            
                
        except:
            print 'hi'


    def using_resource(self):
        try:
            self.session = Session(aws_access_key_id=SP_MD_ACCESS_KEY,
                                   aws_secret_access_key=SP_MD_SECRET_KEY)
            self.s3 = self.session.resource('s3')
            
            # Check if the bucket exists.
            self.s3.meta.client.head_bucket(Bucket=SP_MD_S3_BUCKET)
            
            # Fetch a bucket.
            self.bucket = self.s3.Bucket(SP_MD_S3_BUCKET)
            
            # Save a key to bucket.
            #response = self.bucket.put_object(Key='test', Body='some_text', Metadata={'sp-group': 'RackImpersonation'})
            
            # Fetch all keys from bucket.
            #keys = self.bucket.objects.all()
            #for key in keys:
            #    print key.key
            
            # Fetch specific key from a bucket.
            key = self.s3.Object(SP_MD_S3_BUCKET, 'astra.rackspace.local')
            
            # Fetch object if modified. Not supported.
            # key_obj = key.get(IfModifiedSince='Sun, 31 Jul 2016 23:13:52 GMT')
            key_obj = key.get(IfModifiedSince=datetime(2016, 8, 31))
            # key_obj = key.get()
            print key_obj['Body'].read()
            print key_obj['Metadata']
            
        except Boto3Error as e:
            print 'hi'
        except ClientError as e:
            print 'hi'
            error_code = int(e.response['Error']['Code'])
            if error_code == 404:
                print 'Bucket doesnt exists'


s3_client = S3Client()
