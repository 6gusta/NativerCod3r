const messages = ["Bem Vindo !!", "Pronto para aprender?", "Explore nossos cursos!", "Faça seu login ou se cadastre ou acesse Como Vistante", "Voce vai Aprender na sua jornada liguagens como Python", " Linguas do mundo todo Como Ingles"];
const images = [
    "Imagens/images.png",
    "Imagens/learn-robot-adorable-ai-learning-with-3d-rendering-in-machine-concept_9784581.jpg",
    "Imagens/learning-reading-book-3d-rendering-of-a-robot-engaged-in-machine-by_9785771.jpg",
    "Imagens/image.png",
    "Imagens/python-3-logo.svg",
    "Imagens/logotipo-da-turma-de-língua-inglesa-dos-eua-fórum-do-programa-intercâmbio-línguas-e-sinal-comunicação-internacional-com-229856956.webp"
];
let messageIndex = 0;

const messageElement = document.querySelector('.message');
const imageElement = document.querySelector('.carousel-image');
const intervalTime = 3000; // Tempo em milissegundos (3 segundos)

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

// Função para trocar mensagens e imagens automaticamente
function startCarousel() {
    updateCarousel();
    setInterval(function() {
        messageIndex = (messageIndex === messages.length - 1) ? 0 : messageIndex + 1;
        updateCarousel();
    }, intervalTime);
}

// Inicia o carrossel quando a página carrega
window.onload = startCarousel;



const menuToggle = document.getElementById('menuToggle');
const navMenu = document.getElementById('navMenu');

menuToggle.addEventListener('click', () => {
    navMenu.classList.toggle('show');
});
