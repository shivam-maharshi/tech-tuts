

def copy_func(f, name=None):
    return types.FunctionType(f.func_code, f.func_globals, name or f.func_name,
        f.func_defaults, f.func_closure)

get_item_copy = copy_func(MetadataStore.__getitem__, "get_item_copy")

def get_items_wrapper(*args, **kwargs):
    # import pdb; pdb.set_trace()
    try:
        sync_sp_metadata(identity_provider, args[0])
    except:
        print("error occured")
        pass   
    get_item_copy(identity_provider.metadata, args[0])
    
MetadataStore.__getitem__ = get_items_wrapper
