const handleMenuClick = (state = [], action) => {
    console.log("reducer: handleMenuClick")
    switch (action.type) {
        case 'SET_MENU_TEXT':
            console.log("SET_MENU_TEXT: ", action.menuItem);
            console.log(state);
            var after = Object.assign({}, state, {
                selectedMenu: action.menuItem
            });
            console.log(after)
            return after
        default:
            return state
    }
}

export default handleMenuClick