export function getInitialState(state) {
    return Object.assign(state, {}, {
        filter: 'todo',
        tabs:[{
            name: 'Todo',
            filter: 'todo'
        },{
            name: 'Finished',
            filter: 'finished'
        }],
        list: [{
            id: 0,
            title: '111',
            finished: false
        },{
            id: 1,
            title: '333',
            finished: false
        },{
            id:2,
            title: '222',
            finished: true
        }]
    })
}

const setCourse = (state, action) => {
    if( state.id !== action.id){
        return state;
    }
    return Object.assign({}, state, {
        finished: true
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
        case 'SET_COURSE_DELETE':
            return Object.assign({}, state, {
                list: state.list.map(t => setCourse(t, action))
            })
        default:
            return state
    }
}

export default courses