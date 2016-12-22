from boto.sqs import connect_to_region
import config

class SQSClient(object):
    
    def __init__(self, config):
        self.conn = connect_to_region(config.region,
                                      aws_access_key_id=config.access_key,
                                      aws_secret_access_key=config.secret_key)
        self.queue = self.conn.get_queue(config.queue_name)
        
    def get_message(self):
        self.queue.get_messages(num_messages=1, visibility_timeout=10,)
        messages = self.queue.get_messages(num_messages=1)
        print(messages[0]._body)
        self.queue.delete_message(messages[0])
        return messages[0]
    
sqs_client = SQSClient(config=config)
sqs_client.get_message()