document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.getElementById('menuToggle');
    const navMenu = document.getElementById('navMenu');

    menuToggle.addEventListener('click', () => {
        navMenu.classList.toggle('show');
    });
});



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


document.addEventListener('DOMContentLoaded', function() {
    console.log('Script de resposta carregado');

    // Selecionando os elementos HTML
    const button = document.querySelector('#butttonmundo');
    const printcode = document.getElementById('print-code');
    const printtranslation = document.getElementById('print-translation');
    const printcode2 = document.getElementById('print-code2');
    const printtranslation2 = document.getElementById('print-translation2');
    
    // Definindo os IDs das perguntas
    const idperguntas5 = 13;
    const idperguntas6 = 14;

    // Verificando se o botão de envio existe
    if (button) {
        button.addEventListener('click', checkPrintChallenge);
    } else {
        console.error('Botão de mundo não encontrado!');
    }

    // Função para verificar se todos os campos estão preenchidos
    function verificarCampos() {
        console.log("Verificando se os campos estão preenchidos...");

        // Verificando se todos os campos de resposta estão preenchidos
        if (!printcode || !printtranslation || !printtranslation2 || !printcode2 || 
            !printcode.value.trim() || !printtranslation.value.trim() || 
            !printcode2.value.trim() || !printtranslation2.value.trim()) {
            alert("Por favor, preencha todos os campos de resposta antes de enviar.");
            return false;
        }
        console.log("Campos preenchidos corretamente.");
        return true;
    }

    // Função chamada quando o botão de envio é clicado
    function checkPrintChallenge() {
        console.log("Botão foi clicado! Verificando resposta...");

        const camposPreenchidos = verificarCampos();
        console.log("Resultado de verificarCampos:", camposPreenchidos);

        if (!camposPreenchidos) {
            console.log("Execução interrompida devido a campos vazios.");
            return; // Para a execução se os campos não estiverem preenchidos
        }

        // Pegando o nome do usuário do localStorage
        const usuarioNome = localStorage.getItem("usuarioNome");

        // Estruturando os dados a serem enviados
       
        const registerData = [
            {   
                idpergunta: { idpergunta: idperguntas13 },  // Estrutura aninhada para o backend
                nomeUser: usuarioNome,
                res_user: printtranslation.value,
                repostaAlgoritimoUser: printcode.value,
                VouF: "V"  // Ou outra lógica que defina o valor
            },
            {   
                idpergunta: { idpergunta: idperguntas14 }, 
                nomeUser: usuarioNome,
                res_user: printtranslation2.value,
                repostaAlgoritimoUser: printcode2.value,
                VouF: "F"
            }
        ];
        

        console.log("Dados a serem enviados:", registerData);


        fetch('http://localhost:8080/api/login/respostauser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(registerData),  // Envia os dados como JSON
            mode: 'cors'
        })
        .then(response => response.text())
        .then(message => {
            console.log("Mensagem recebida do backend:", message);  // Verifique a resposta exata
             
            // Remover espaços extras
            const cleanedMessage = message.trim();
            console.log("Mensagem sem espaços:", cleanedMessage);  // Verifique a string sem espaços extras
        
            // Comparação do valor esperado e a resposta
            const respostaEsperada = 'questão correta!'.toLowerCase().trim();
            console.log("Valor esperado:", respostaEsperada);  // Log para ver o valor esperado
        
            // Comparação insensível a maiúsculas/minúsculas e espaços extras
            if (cleanedMessage === respostaEsperada) {
                alert("Você acertou!");
            } else {
                alert("Resposta incorreta!");
                console.error('Resposta incorreta!');
            }
        })
        .catch(error => {
            console.error('Erro ao processar a resposta:', error);
            alert('Erro ao processar a resposta: ' + error.message);
        });
    }

    
});

