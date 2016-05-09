import {SET_MENU_TEXT} from './../actions/headerAction'

const headerMenu = (state = [], action = {}) => {
    
    switch (action.type) {
        case SET_MENU_TEXT:
            return Object.assign({}, state, {
                selectedMenu: action.menuItem
            });
        default:
            return state
    }
}


export default headerMenu