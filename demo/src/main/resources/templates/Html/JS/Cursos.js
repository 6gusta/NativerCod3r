
const menuToggle = document.getElementById('menuToggle');
const navMenu = document.getElementById('navMenu');

menuToggle.addEventListener('click', () => {
    navMenu.classList.toggle('show');
});


const teste = document.getElementsByClassName('fa fa-lock');
Array.from(teste).forEach((element) => {
  element.addEventListener('click', () => {
    alert("Você precisa terminar o módulo 1 para prosseguir");
  });
});


function getUsuarioLogadoId() {
    // Lógica para obter o ID do usuário logado
    return localStorage.getItem('usuarioId'); // Ajuste conforme necessário
}

function iniciarRegistro(element) {
    const mundoId = element.getAttribute('data-mundo');

    // Obtém o ID do usuário logado
    const usuarioId = getUsuarioLogadoId();

    // Verifica se o ID do usuário é válido
    if (!usuarioId) {
        console.error('Usuário não está logado.');
        return; // Não prosseguir se o usuário não estiver logado
    }

    // Requisição para associar o mundo ao usuário logado
    fetch('/api/login/assignMundo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: usuarioId,  // ID do usuário logado
            mundoid: mundoId // ID do mundo selecionado
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao associar o mundo: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log('Mundo associado com sucesso:', data);
        // Redireciona para a página do mundo
        window.location.href = `Mundo${mundoId}.html?id=${mundoId}`;
    })
    .catch(error => {
        console.error('Erro ao associar o mundo:', error);
    });
}
