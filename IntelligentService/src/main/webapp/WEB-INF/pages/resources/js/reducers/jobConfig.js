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
            title: '111',
            open: false
        },{
            title: '222',
            open: true
        },{
            title: 'hotjob333',
            open: true
        }]
    })
}


const jobConfig = (state = {}, action) => {
    switch (action.type) {
        case 'ADD':
            return state
        case 'SET_JOBCONFIG_FILTER':
            return Object.assign({}, state, {
                filter: action.filter
            })
        default:
            return state
    }
}

export default jobConfig