export function getInitialState(state) {
    return Object.assign(state, {}, {
        filter: 'todo',
        list: [{
            title: '111',
            finished: false
        },{
            title: '222',
            finished: true
        }]
    })
}


const courses = (state = {}, action) => {
    switch (action.type) {
        case 'ADD':
            return state
        case 'SET_COURSES_FILTER':
            return Object.assign({}, state, {
                filter: action.filter
            })
        default:
            return state
    }
}

export default courses