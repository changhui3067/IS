/**
 * Created by freyjachang on 5/3/16.
 */
export function handleNotiDeleteClick(id) {
    return {
        type: 'NOTI_DELETE',
        id: id
    }
}

export function handleNotiReadClick(id) {
    return {
        type: 'NOTI_READ',
        id: id
    }
}