export function getInitialState(state) {
    return Object.assign(state, {}, {
        filter: 'open',
        tabs:[{
            name: 'Open',
            filter: 'open'
        },{
            name: 'Closed',
            filter: 'closed'
        }],
        list: [{
            id: 0,
            title: '111',
            open: true
        },{
            id: 1,
            title: '222',
            open: true
        },{
            id: 2,
            title: 'hotjob333',
            open: false
        }]
    })
}

const setClose = (state, action) => {
    if(state.id !== action.id) {
        return state
    }
    return Object.assign({}, state, {
        open: false
    })
}

const findhotjob = (item, id) => {
    return item.id === id
}

const jobConfig = (state = {}, action) => {
    switch (action.type) {
        case 'ADD':
            return state
        case 'SET_JOBCONFIG_FILTER':
            return Object.assign({}, state, {
                filter: action.filter
            })
        case 'SET_JOBCONFIG_CLOSE':
            return Object.assign({}, state, {
                list: state.list.map(t => setClose(t, action))
            })
        case 'SET_JOBCONFIG_DELETE':
            var temp = Object.assign({}, state, {})
            var indexx = state.list.findIndex((t) => findhotjob(t, action.id))
            var deleteItem = temp.list.splice(indexx, 1);
            return temp
        default:
            return state
    }
}

export default jobConfig