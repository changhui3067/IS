export function getInitialState(state) {
    return Object.assign(state, {}, {
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


const empConfig = (state = {}, action) => {
    switch (action.type) {
        case 'EMPCONFIG_ADD':
            console.log('emp config add click')
            return state
        
        default:
            return state
    }
}

export default empConfig