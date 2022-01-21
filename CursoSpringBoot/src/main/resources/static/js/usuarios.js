// Call the dataTables jQuery plugin
$(document).ready(function() {
  actualizarEmailUsuario()
  cargarUsuarios()
  $('#tablaUsuarios').DataTable();
});

function actualizarEmailUsuario(){
    document.getElementById("txt-email-usuario").outerHTML = localStorage.email;
}



function getHeaders(){
    return{
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    }
}

async function cargarUsuarios(){

    const request = await fetch('api/usuarios', {
      method: 'GET',
      headers: getHeaders()
    });
    const usuariosJson = await request.json();


    console.log(usuariosJson);
    let listadoHtml = '';
    for (let usuario of usuariosJson){

        let botonEliminar ="<a href=\"#\" onclick='eliminarUsuario("+usuario.id+")' class=\"btn btn-danger btn-circle btn-sm\">\n" +
            "<i class=\"fas fa-trash\"></i>\n" +
            "</a>";

        let telefonoTexto = usuario.telefono == null ? '-' : usuario.telefono;

        let usuarioHtml = "<tr>\n" +
            "<td>"+usuario.id+"</td>\n" +
            "<td>"+usuario.nombre+" "+usuario.apellido+"</td>\n" +
            "<td>"+usuario.email+"</td>\n" +
            "<td>"+telefonoTexto+"</td>\n" +
            "<td>"+botonEliminar+"</td>\n" +
            "</tr>"
        listadoHtml += usuarioHtml;
    }

    document.querySelector('#tablaUsuarios tbody').outerHTML = listadoHtml;
}


async function  eliminarUsuario(id){

    if(!confirm("¿Desea eliminar este usuario? id: "+id)){
        return;
    }
     const request = await fetch('api/usuarios/'+ id, {
        method: 'DELETE',
        headers: getHeaders()
    });
    location.reload();
}