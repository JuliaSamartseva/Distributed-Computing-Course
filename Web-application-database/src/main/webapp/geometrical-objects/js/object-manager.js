window.onload = async () => {
    let result = findGetParameter("id")
    if (result != null) {
        const res = await fetch(`../servlets/objects/edit?id=${result}`);
        const request = await res.json();
        document.getElementById('name').value = request.name
        document.getElementById('type').value = request.type
        document.getElementById('sides').value = request.sides

        const createCoordinates =
            (coordinates) => {
                let resultArray = [];
                for (let i = 0; i < coordinates.length; i++) {
                    if (i === 0) {
                        resultArray.push("(", coordinate.x, ", ", coordinate.y, "),")
                    } else if (i === coordinates.length - 1) {
                        resultArray.push(" (", coordinate.x, ", ", coordinate.y, ")")
                    } else {
                        resultArray.push(" ,(", coordinate.x, ", ", coordinate.y, ")")
                    }
                }
                return resultArray.join("")
            };

        document.getElementById('points').value = createCoordinates(request.coordinates)
    }
}



const checkFormInputs =
    async () => {
        document.getElementById('product-submit').disabled = true;

        const data = new Map([
            ['product-name', document.getElementById('name').value],
            ['price', document.getElementById('type').value],
            ['description', document.getElementById('sides').value],
            ['type', document.getElementById('points').value],
        ]);

        const encodedData = [];
        data.forEach((value, key) => {
            encodedData.push(`${key}=${value}`);
        });

        const editId = findGetParameter("id")
        // Add object to the db

        let response = null;
        if (editId == null) {
            const body = encodedData.join('&');
            response = await fetch('../servlets/objects/add', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: body
            });
        } else { // Edit product with the given id from the get request
            encodedData.push(`id=${editId}`)
            const body = encodedData.join('&');
            response = await fetch('../servlets/objects/edit', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: body
            });
        }

        if (response != null && response.ok) {
            window.location.href = ``
        } else {
            formFailure("An unexpected error occurred. Please try again.")
        }

    }

function findGetParameter(parameterName) {
    let result = null,
        tmp = [];
    location.search
        .substr(1)
        .split("&")
        .forEach(function (item) {
            tmp = item.split("=");
            if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
        });
    return result;
}