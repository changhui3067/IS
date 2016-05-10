export const SET_HOTJOBS_FILTER = 'SET_HOTJOBS_FILTER'
export const SET_HOTJOB_APPLY = 'SET_HOTJOB_APPLY'
export const SET_HOTJOBS_DIALOG_VISIBLE = 'SET_HOTJOBS_DIALOG_VISIBLE'
export const SET_HOTJOBS = 'SET_HOTJOBS'

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

export function receiveHotjobs() {
    return {
        type: SET_HOTJOBS
    }
}

export function getHotjobs() {
    $.ajax({
        url: 'api/',
        type: "POST",
        //dataType: "json",
        contentType: "application/json",
        success: function(response){
            console.log('success')
            var json1 = response.json()

            dispatch(receiveHotjobs('frontend', json1))
        },//.bind(this),
        error: function(xhr, status, err){
            console.log(status, err.toString());
        }//.bind(this)
    })
}