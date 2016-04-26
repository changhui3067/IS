import { combineReducers } from 'redux'
import headerMenu from './headerMenu'
import employee from './employee'
import tilesInfo from './tilesInfo'
import courses from './courses'

const reducers = combineReducers({
    headerMenu,
    employee,
    courses
})

export default reducers