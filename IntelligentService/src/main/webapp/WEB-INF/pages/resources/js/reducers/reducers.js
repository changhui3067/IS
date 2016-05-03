import { combineReducers } from 'redux'
import headerMenu from './headerMenu'
import employee from './employee'
import tilesInfo from './tilesInfo'
import courses from './courses'
import hotjobs from './hotjobs'
import empConfig from './empConfig'
import jobConfig from './jobConfig'
import courseConfig from './courseConfig'
import subConfig from './subConfig'

const reducers = combineReducers({
    headerMenu,
    employee,
    courses,
    hotjobs,
    empConfig,
    jobConfig,
    courseConfig,
    subConfig
})

export default reducers