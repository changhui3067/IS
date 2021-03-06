import { combineReducers } from 'redux'
import headerMenu from './headerMenu'
import employee from './employee'
import courses from './courses'
import hotjobs from './hotjobs'
import empConfig from './empConfig'
import jobConfig from './jobConfig'
import courseConfig from './courseConfig'
import subConfig from './subConfig'
import notification from './notification'

const reducer = combineReducers({
    headerMenu,
    employee,
    courses,
    hotjobs,
    empConfig,
    jobConfig,
    courseConfig,
    subConfig,
    notification
})

export default reducer