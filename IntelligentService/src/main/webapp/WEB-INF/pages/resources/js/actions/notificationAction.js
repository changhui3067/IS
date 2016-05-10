/**
 * Created by freyjachang on 5/3/16.
 */
import $ from "jquery";

export const NOTI_DELETE = 'NOTI_DELETE'
export const NOTI_READ = 'NOTI_READ'

export function handleNotiDeleteClick(id) {
    return {
        type: NOTI_DELETE,
        id: id
    }
}

export function handleNotiReadClick(id) {
    return {
        type: NOTI_READ,
        id: id
    }
}

export function receivePosts(reddit, json) {
    console.log('receivePosts')
    return {
        type: 'RECEIVE_POSTS',
        reddit,
        posts: json.data.children.map(child => child.data),
        receivedAt: Date.now()
    }
}

export function getNotification() {
    console.log('action, getNotification')
    $.ajax({
        url: 'api/',
        type: "POST",
        //dataType: "json",
        contentType: "application/json",
        success: function(response){
            console.log('success')
            var json1 = response.json()

            dispatch(receivePosts('frontend', json1))
        },//.bind(this),
        error: function(xhr, status, err){
            console.log(status, err.toString());
        }//.bind(this)
    })
}