single_post_response_example={
    'code': 200,
    'message': "success!",
    'data':{
        "post": post_json_example
    }
}
single_comment_response_example={
    'code': 200,
    'message': "success!",
    'data':{
        "post": comment_json_example
    }
}
let xxx=null
let true_or_false=null
let up_or_down_or_none=null
post_json_example={
    'user_email': xxx,
    'identity': xxx,
    'user_name': xxx,
    'ups_downs': {
        'ups': xxx,
        'downs': xxx,
        'activated': 'up' / 'down' / 'none'
    },
    'time_stamp': xxx,
    'comments_closed': true_or_false,
    'content': xxx,
    'comments':['identities']
}

comment_json_example={
    'user_email': xxx,
    'identity': xxx,
    'user_name': xxx,
    'ups_downs': {
        'ups': xxx,
        'downs': xxx,
        'activated': up_or_down_or_none
    },
    'time_stamp': xxx,
    'content': xxx
}

posts_list_response_example={
    'code': 200,
    'message': 'success!',
    'data':{
        'posts_list': []
    }
}

