
export function getWebImage(id){
    const url = `/api/file/${id}`;
    console.log(url);
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'GET'
    } )
        .then((response) => response.blob())
        .then((image) => {
            let blobUrl = URL.createObjectURL(image);
            console.log(blobUrl);
            return blobUrl;
        });
}