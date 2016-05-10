/**
 * Created by freyjachang on 5/3/16.
 */
import {NOTI_READ, NOTI_DELETE} from './../actions/notificationAction'

export function getInitialState(state) {
    return Object.assign(state, {}, {
        unreadcount: 3,
        list: [{
            title: 'this is notification 1',
            isread: false,
            date: '2016-05-02'
        },{
            title: 'this is notification 2',
            isread: false,
            date: '2016-05-01'
        },{
            title: 'this is notification 3',
            isread: true,
            date: '2016-05-01'
        }]
    })
}

const setRead = (state, index, action) => {
    if( index === action.id) {
        return Object.assign({}, state, {
            isread: true
        })
    }
    return state
}

function posts(state = {}, action ={}) {
    switch (action.type) {
        case INVALIDATE_REDDIT:
            return Object.assign({}, state, {
                didInvalidate: true
            })
        case REQUEST_POSTS:
            return Object.assign({}, state, {
                isFetching: true,
                didInvalidate: false
            })
        case RECEIVE_POSTS:
            return Object.assign({}, state, {
                isFetching: false,
                didInvalidate: false,
                list: action.posts,
                lastUpdated: action.receivedAt
            })
        default:
            return state
    }
}

const notification = (state = {isFetching: false,
    didInvalidate: false,
    list: []}, action = {}) => {
    switch (action.type) {
        case NOTI_DELETE:
            var temp = Object.assign({}, state, {
                unreadcount: state.unreadcount -1
            })
            var deleteItem = temp.list.splice(action.id, 1);

            console.log('delete', deleteItem)
            return temp
        case NOTI_READ:
            var temp = Object.assign({}, state, {
                unreadcount: state.unreadcount - 1
            })
            var temp1 = Object.assign({}, temp, {
                list: state.list.map( (t, index) => setRead(t, index, action))
            })
            console.log('read', temp1)
            return temp1
        case 'RECEIVE_POSTS':
            console.log('reducer ', 'RECEIVE_POSTS')
            return Object.assign({}, state, {
                [action.reddit]: posts(state[action.reddit], action)
            })
        default:
            return state
    }
}

export default notification
