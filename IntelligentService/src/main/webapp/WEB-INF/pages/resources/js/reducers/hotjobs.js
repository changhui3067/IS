export function getInitialState(state) {
    return Object.assign(state, {}, {
        filter: 'open',
        tabs: [{
            name: 'Open',
            filter: 'open'
        },{
            name: 'Applied',
            filter: 'applied'
        }],
        list: [{
            title: 'hotjob1',
            applied: false
        },{
            title: 'hotjob2',
            applied: true
        }]
    })
}


const hotjobs = (state = {}, action) => {
    switch (action.type) {
        case 'ADD':
            return state
        case 'SET_HOTJOBS_FILTER':
            return Object.assign({}, state, {
                filter: action.filter
            })
        default:
            return state
    }
}

export default hotjobs