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
            title: 'custom course',
            default: false
        },{
            title: 'default course1',
            default: true
        },{
            title: 'default courses2',
            default: true
        }]
    })
}


const courseConfig = (state = {}, action) => {
    switch (action.type) {
        case 'ADD':
            return state
        case 'SET_COURSECONFIG_FILTER':
            return Object.assign({}, state, {
                filter: action.filter
            })
        default:
            return state
    }
}

export default courseConfig