
const handleMenuClick = (state = [], action) => {
    console.log("reducer: handleMenuClick")
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

const handleTileClick = (state, action) => {
    console.log("reducer: handleTileClick")
    return handleMenuClick(state, action);
}


export default {handleMenuClick, handleTileClick}