const handleMenuClick = (state = [], action) => {
  switch (action.type) {
    case 'SET_MENU_TEXT':
      return Object.assign({}, state, {
        selectedMenu: action.type
      })
    default:
        return state
  }
}

export default handleMenuClick