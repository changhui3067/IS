export const SET_HOTJOBS_FILTER = 'SET_HOTJOBS_FILTER'
export const SET_HOTJOB_APPLY = 'SET_HOTJOB_APPLY'
export const SET_HOTJOBS_DIALOG_VISIBLE = 'SET_HOTJOBS_DIALOG_VISIBLE'

export function handleHotjobsTabClick(filter) {
    return {
        type: SET_HOTJOBS_FILTER,
        filter: filter
    }
}

export function handleHotjobsApplyClick(id) {
    return {
        type: SET_HOTJOB_APPLY,
        id
    }
}

export function handleHotjobsSetDialogShow(visible, id) {
    return {
        type: SET_HOTJOBS_DIALOG_VISIBLE,
        visible: visible,
        id: id
    }
}