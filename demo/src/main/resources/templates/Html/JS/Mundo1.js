const messages = ["Bem Vindo !! ao seu primeiro mundo de python e ingles ", "no nosso primeiro mundo iremos resolver alguns desafios ultilzando python e a ligua inglesa", "assikm que tiver as reposta basta clica no botão enviar que voce vera o resultado ", "seu progresso so sera salvo caso tenha uma conta ", "qualquer duvida basta ir em FAQ", " BOA SORTE!!"];
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
