import {EMPCONFIG_ADD,
        SET_EMPCONFIG_DELETE,
        SET_EMPCONFIG_DIALOG_VISIBLE} from './../actions/empConfigAction'

export function getInitialState(state) {
    return Object.assign(state, {}, {
        dialog: {
            visible: false,
            id: -1,
            field: {}
        },
        list: [{
            username: 'freyja',
            userphoto: 'userphoto.jpg',
            userid: 5
        },{
            username: 'coworker1',
            userphoto: 'userphoto.jpg',
            userid: 2
        },{
            username: 'coworker2',
            userphoto: 'userphoto.jpg',
            userid: 3
        },{
            username: 'coworker3',
            userphoto: 'userphoto.jpg',
            userid: 4
        }]
    })
}

const findemp = (item, id) => {
    return item.id === id
}

const empConfig = (state = {}, action = {}) => {
    switch (action.type) {
        case EMPCONFIG_ADD:
            return state
        case SET_EMPCONFIG_DELETE:
            var temp = Object.assign({}, state, {})
            var indexx = state.list.findIndex((t) => findemp(t, action.id))
            var deleteItem = temp.list.splice(indexx, 1);
            return temp
        case SET_EMPCONFIG_DIALOG_VISIBLE:
            var dialog = {
                dialog: Object.assign({}, state.dialog, {
                    visible: action.visible,
                    id: action.id
                })
            }
            return Object.assign({}, state, dialog)
        default:
            return state
    }
}

export default empConfig