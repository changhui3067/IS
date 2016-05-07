
const headerMenu = (state = [], action = {}) => {
    
    switch (action.type) {
        case 'SET_MENU_TEXT':
            console.log("SET_MENU_TEXT: ", action.menuItem);
            return Object.assign({}, state, {
                selectedMenu: action.menuItem
            });
        default:
            return state
    }
}


export default headerMenu