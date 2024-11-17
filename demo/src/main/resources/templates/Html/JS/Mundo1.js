const messages = ["Bem Vindo !! ao seu primeiro mundo de python e ingles ", "no nosso primeiro mundo iremos resolver alguns desafios ultilzando python e a ligua inglesa", "assikm que tiver as reposta basta clica no botão enviar que voce vera o resultado ", "seu progresso so sera salvo caso tenha uma conta ", "qualquer duvida basta ir em FAQ", " BOA SORTE!!"];
let messageIndex = 0;

const messageElement = document.querySelector('.message');
const imageElement = document.querySelector('.carousel-image');
const intervalTime = 3000; 

function updateCarousel() {
    messageElement.textContent = messages[messageIndex];
    imageElement.src = images[messageIndex];
}

document.querySelector('.prev').addEventListener('click', function() {
    messageIndex = (messageIndex === 0) ? messages.length - 1 : messageIndex - 1;
    updateCarousel();
});

document.querySelector('.next').addEventListener('click', function() {
    messageIndex = (messageIndex === messages.length - 1) ? 0 : messageIndex + 1;
    updateCarousel();
});


const menuToggle = document.getElementById('menuToggle');
const navMenu = document.getElementById('navMenu');

menuToggle.addEventListener('click', () => {
    navMenu.classList.toggle('show');
});


function toggleMenu() {
    const aulas = document.querySelector('.Aulas');
    if (aulas.style.display === "none" || aulas.style.display === "") {
        aulas.style.display = "block"; // Mostra o menu
    } else {
        aulas.style.display = "none"; // Esconde o menu
    }
}




document.addEventListener('DOMContentLoaded', function() {
    console.log('Script de reposta  carregado');


    const button = document.querySelector('#butttonmundo'); 
    const printcode = document.getElementById('print-code');
    const printtranslation = document.getElementById('print-translation');
    const printcode2 = document.getElementById('print-code2');
    const printtranslation2 = document.getElementById('print-translation2');

   

    if (button) {
        button.addEventListener('click', checkPrintChallenge);
    } else {
        console.error('Botão de mundo não encontrado!');
    }

    function verificarCampos() {
        console.log("Verificando se os campos estão preenchidos...");

        if (!printcode || !printtranslation || !printtranslation2 || !printcode2 ||!printcode.value.trim() || !printtranslation.value.trim()  ||!printcode2.value.trim() || !printtranslation2.value.trim()) {
            alert("Por favor, preencha todos os campos de reposta antes de enviar .");
         
            return false;
        }
        console.log("Campos preenchidos corretamente.");
        return true;
    }


    function checkPrintChallenge() {
        console.log("Botão foi clicado! Verificando resposta...");

        const camposPreenchidos = verificarCampos();
        console.log("Resultado de verificarCampos:", camposPreenchidos);

        if (!camposPreenchidos) {
            console.log("Execução interrompida devido a campos vazios.");
            return; // Para a execução se os campos não estiverem preenchidos
        }

        const usuarioNome = localStorage.getItem("usuarioNome");

        const registerData = [
            {   nomeUser: usuarioNome,
                res_user: printtranslation.value,
                repostaAlgoritimo: printcode.value,
            },
            {   nomeUser: usuarioNome,
                res_user: printtranslation2.value,
                repostaAlgoritimo: printcode2.value,
            }
        ];
        


        fetch('http://localhost:8080/api/login/repostauser', {
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

            // Verificando a resposta e mostrando um alerta se for correta
            if (message.trim() === 'questão correta!') {
                alert("Você acertou!");
            } else {
                console.error('Resposta incorreta!');
            }
        })
        .catch(error => {
            alert('Erro ao processar a resposta: ' + error.message);
        });
    }
});
