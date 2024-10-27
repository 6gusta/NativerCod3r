
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


function iniciarRegistro(element) {
    const mundoId = element.getAttribute('data-mundo');

    // Chamada para registrar (sem ID do usuário)
    fetch('/api/login/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            // Campos necessários para o registro, se houver
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao registrar: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log('Registro realizado com sucesso:', data);

        // Requisição para associar o mundo
        fetch('/api/login/assignMundo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                mundoId: mundoId // Apenas associando o mundo
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
            window.location.href = `Mundo1.html?id=${mundoId}`;
        })
        .catch(error => {
            console.error('Erro ao associar o mundo:', error);
        });
    })
    .catch(error => {
        console.error('Erro ao registrar:', error);
    });
}
