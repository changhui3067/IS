/**
 * Created by freyjachang on 5/3/16.
 */
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

const notification = (state = {}, action = {}) => {
    switch (action.type) {
        case 'NOTI_DELETE':
            var temp = Object.assign({}, state, {
                unreadcount: state.unreadcount -1
            })
            var deleteItem = temp.list.splice(action.id, 1);

            console.log('delete', deleteItem)
            return temp
        case 'NOTI_READ':
            var temp = Object.assign({}, state, {
                unreadcount: state.unreadcount - 1
            })
            var temp1 = Object.assign({}, temp, {
                list: state.list.map( (t, index) => setRead(t, index, action))
            })
            console.log('read', temp1)
            return temp1
        default:
            return state
    }
}

export default notification
