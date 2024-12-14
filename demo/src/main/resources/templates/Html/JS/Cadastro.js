document.addEventListener('DOMContentLoaded', function() {
    
    const registerButton = document.querySelector('#registerButton');
    if (registerButton) {
        registerButton.addEventListener('click', registerButton);
    } else {
        console.error('Botão de cadastro não encontrado!');
    }
});

document.addEventListener('DOMContentLoaded', function() {
    console.log('Script de cadastro carregado');

    const registerButton = document.getElementById('registerButton');
    if (registerButton) {
        registerButton.addEventListener('click', function() {
            const username = document.getElementById('registerUser').value;
            const password = document.getElementById('registerPassword').value;
            const email = document.getElementById('registeremail').value;
            const telefone = document.getElementById('registerTelefone').value;
            const dataDeNasc = document.getElementById('registerDatanasc').value;
            const rua = document.getElementById('registerrua').value; // Corrigido para 'registerrua'
            const lote = document.getElementById('registerlote').value; // Corrigido para 'registerlote'
            const quadra = document.getElementById('registerquadra').value; // Corrigido para 'registerquadra'
            const bairro = document.getElementById('registerbairro').value; // Corrigido para 'registerbairro'
            const cep = document.getElementById('registercep').value; // Corrigido para 'registercep'
            const cidade = document.getElementById('registercidade').value; // Corrigido para 'registercidade'
            const estado = document.getElementById('registerestado').value; // Corrigido para 'registerestado'
            const sexo = document.getElementById('registerSexo').value;

            if (!username || !password || !email || !telefone || !dataDeNasc || !rua || !lote || !quadra || !bairro || !cep || !cidade || !estado || !sexo) {
                alert("Por favor, preencha todos os campos.");
                return;
            }

            

            const registerData = {
                login: {
                    users: username,
                    senha: password,
                    datanasc : dataDeNasc,
                    sexo : sexo

                },
                email: {
                    emailuser: email
                },
                telefone: {
                    telefoneuser: telefone
                },
                endereco: {
                    rua: document.getElementById('registerrua').value,
                    quadra: document.getElementById('registerquadra').value,
                    lote: document.getElementById('registerlote').value,
                    cep: document.getElementById('registercep').value,
                    bairro: document.getElementById('registerbairro').value,
                    cidade: document.getElementById('registercidade').value,
                    estado: document.getElementById('registerestado').value
                }
            };

            fetch('http://localhost:8080/api/login/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(registerData)
            })
            .then(response => response.text())
            .then(message => {
                console.log("Mensagem recebida do backend:", message);
                alert(message);

                if (message.trim() === 'Cadastro bem-sucedido!') {
                    const caixaCadastro = document.querySelector('#CaixaCadastro');
                    const caixaLogin = document.querySelector('#CaixaLogin');
                    if (caixaCadastro && caixaLogin) {
                        caixaCadastro.style.display = 'none';
                        caixaLogin.style.display = 'block';
                        setTimeout(() => {
                            window.location.href = 'PagInicail.html'; 
                        }, 500);
                    } else {
                        console.error('Elementos de cadastro ou login não encontrados!');
                        window.location.href = 'Login.html';
                    }
                }
            })
            .catch(error => {
                alert('Erro ao processar o cadastro: ' + error.message);
            });
        });
    } else {
        console.error('Botão de registro não encontrado!');
    }
});




function togglePasswordVisibility(inputId, iconId) {
    const campoSenha = document.getElementById(inputId);
    const eyeIcon = document.getElementById(iconId);

    if (campoSenha.type === 'password') {
        campoSenha.type = 'text';
        eyeIcon.classList.remove('fa-eye-slash');
        eyeIcon.classList.add('fa-eye');
    } else {
        campoSenha.type = 'password';
        eyeIcon.classList.remove('fa-eye');
        eyeIcon.classList.add('fa-eye-slash');
    }
}


const menuToggle = document.getElementById('menuToggle');
const navMenu = document.getElementById('navMenu');

menuToggle.addEventListener('click', () => {
    navMenu.classList.toggle('show');
});


