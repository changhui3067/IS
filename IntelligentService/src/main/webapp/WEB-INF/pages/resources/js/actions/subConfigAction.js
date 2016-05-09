/**
 * Created by freyjachang on 5/2/16.
 */

export const SET_SUBCONFIG_SWITCHER = 'SET_SUBCONFIG_SWICHER'
export const SET_SUBTAB_FILTER = 'SET_SUBTAB_FILTER'

export function handleSubConfigSwitchChanged(switcher) {
    return {
        type: SET_SUBCONFIG_SWITCHER,
        switcher: switcher
    }
}

export function handleSubConfigTabClick(tab) {
    return{
        type: SET_SUBTAB_FILTER,
        filter: tab
    }
}