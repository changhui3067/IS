import { combineReducers } from 'redux'
import headerMenu from './headerMenu'
import employee from './employee'
import tilesInfo from './tilesInfo'

const reducers = combineReducers({
    headerMenu,
    employee
})

export default reducers