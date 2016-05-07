/**
 * Created by freyjachang on 4/21/16.
 */
export function getUsername(state) {
    return 'Freyja'
    // state.employee.username || 'Freyja'

}

export function getUserphoto(state) {
    return 'userphoto.jpg'
    // state.userphoto || 'userphoto.jpg'
}

export function getUsertype(state) {
    return 0//state.usertype || 0
}

export function getManagerInfo(state) {
    return {
        username: 'manager',
        userphoto: 'userphoto.jpg',
        userid: 1
    }
}

export function getCoworkersInfoList(state) {
    return [{
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
}

export function getSubordinatesInfoList(state) {
    return [{
        username: 'subordinate1',
        userphoto: 'userphoto.jpg',
        userid: 6
    },{
        username: 'subordinate2',
        userphoto: 'userphoto.jpg',
        userid: 7
    }]
}

export function getUserInfo(state) {
    return {
        name: 'Freyja',
        position: 'project manager',
        level: 'admin',
        email: '111@email.com',
        phonenumber: '11111111111',
        photo: 'userphoto.jpg'
    }
}

const employee = (state = [], action = {}) => {
    
    switch (action.type) {
        case 'SET_PROFILE_TEXT':
            var o = new Object()
            o[action.field] = action.text
            console.log(Object.assign({}, state, o))
            return Object.assign({}, state, o);
        default:
            return state
    }
}


export default employee