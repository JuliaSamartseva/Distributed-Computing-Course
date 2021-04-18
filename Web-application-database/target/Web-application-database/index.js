window.onload = async () => {
    document.getElementById('load').onclick = async function () {
        await fetch(`./servlets/xml/load`);
        window.location.reload();
    }

    document.getElementById('upload').onclick = async function () {
        await fetch(`./servlets/xml/upload`);
        window.location.reload();
    }

    await populateTable();
}

const populateTable = async () => {
    const table = document.getElementById('geometricalObjects');
    const res = await fetch('./servlets/objects');
    const requests = await res.json();

    const createRow =
        (idText, nameText, typeText, sidesText, pointsText) => {
            const row = document.createElement('tr');

            const id = document.createElement('td');
            const name = document.createElement('td');
            const type = document.createElement('td');
            const sides = document.createElement('td');
            const points = document.createElement('td');

            id.innerText = idText;
            name.innerText = nameText;
            type.innerText = typeText;
            sides.innerText = sidesText;
            points.innerText = pointsText;

            row.appendChild(id);
            row.appendChild(name);
            row.appendChild(type);
            row.appendChild(sides);
            row.appendChild(points);

            return row;
        };

    const changeButtons =
        (id) => {
            const actions = document.createElement('td');
            let editButton = document.createElement("button");
            editButton.innerText = "edit";
            editButton.className = "badge badge-dark";
            editButton.style.marginRight = "10px";
            editButton.onclick = function () {
                window.location = `http://localhost:8080/Web_application_database_war/geometrical-objects/object-manager.jsp?id=${id}`;
            }
            let removeButton = document.createElement("button");
            removeButton.innerText = "remove";
            removeButton.className = "badge badge-dark";
            removeButton.onclick = async function () {
                await fetch(`./servlets/objects/remove?id=${id}`);
                window.location.reload();
            }
            actions.appendChild(editButton);
            actions.appendChild(removeButton);

            return actions
        }

    const createCoordinates =
        (coordinates) => {
            let resultArray = [];
            for (let i = 0; i < coordinates.length; i++) {
                let coordinate = coordinates[i]
                if (i === 0) {
                    resultArray.push("(", coordinate.x, ", ", coordinate.y, "), ")
                } else if (i === coordinates.length - 1) {
                    resultArray.push("(", coordinate.x, ", ", coordinate.y, ")")
                } else {
                    resultArray.push("(", coordinate.x, ", ", coordinate.y, "), ")
                }
            }
            return resultArray.join("")
        };

    for (let request of requests) {
        const row = createRow(request.id, request.name, request.type, request.sides, createCoordinates(request.coordinates.point));
        row.appendChild(changeButtons(request.id));
        table.appendChild(row);
    }
};