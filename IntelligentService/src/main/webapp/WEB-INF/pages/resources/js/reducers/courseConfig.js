export function getInitialState(state) {
    return Object.assign(state, {}, {
        filter: 'default',
        tabs:[{
            name: 'Default',
            filter: 'default'
        },{
            name: 'Custom',
            filter: 'custom'
        }],
        list: [{
            id: 0,
            title: 'custom course',
            default: false
        },{
            id: 1,
            title: 'default course1',
            default: true
        },{
            id: 2,
            title: 'default courses2',
            default: true
        }]
    })
}

const findcourse = (item, id) => {
    return item.id === id
}

const courseConfig = (state = {}, action) => {
    switch (action.type) {
        case 'ADD':
            return state
        case 'SET_COURSECONFIG_FILTER':
            return Object.assign({}, state, {
                filter: action.filter
            })
        case 'SET_COURSECONFIG_DELETE':
            var temp = Object.assign({}, state, {})
            var indexx = state.list.findIndex((t) => findcourse(t, action.id))
            var deleteItem = temp.list.splice(indexx, 1);
            return temp
        default:
            return state
    }
}

export default courseConfig