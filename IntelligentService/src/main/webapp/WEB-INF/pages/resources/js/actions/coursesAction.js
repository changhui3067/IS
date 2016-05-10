export const SET_COURSES_FILTER = 'SET_COURSES_FILTER'
export const SET_COURSE_DELETE = 'SET_COURSE_DELETE'
export const SET_COURSES_DIALOG_VISIBLE = 'SET_COURSES_DIALOG_VISIBLE'
export const SET_COURSES = 'SET_COURSES'

export function handleCoursesTabClick(filter) {
    return {
        type: SET_COURSES_FILTER,
        filter: filter
    }
}

export function handleCoursesFinishedClick(id) {
    return {
        type: SET_COURSE_DELETE,
        id: id
    }
}

export function handleCoursesSetDialogShow(visible, id) {
    return {
        type: SET_COURSES_DIALOG_VISIBLE,
        visible: visible,
        id: id
    }
}

export function reveiveCourses() {
    return {
        type: SET_COURSES
    }
}

export function getCourses() {
    console.log('action, getNotification')
    $.ajax({
        url: 'https://www.reddit.com/r/frontend.json',
        type: "POST",
        //dataType: "json",
        contentType: "application/json",
        success: function(response){
            console.log('success')
            var json1 = response.json()
            dispatch(receivePosts('frontend', json1))

        },//.bind(this),
        error: function(xhr, status, err){
            console.log(status, err.toString());
        }//.bind(this)
    })
}

