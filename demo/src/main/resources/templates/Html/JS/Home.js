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


function togglePasswordVisibility() {
    // Seleciona o campo de entrada de senha e o ícone de olho
    const campoSenha = document.getElementById('senha');
    const eyeIcon = document.getElementById('eyeIcon');

    // Verifica o tipo atual do campo de entrada de senha
    if (campoSenha.type === 'password') {
        // Se for 'password', mude para 'text' para mostrar a senha
        campoSenha.type = 'text';
        // Altere o ícone para 'fa-eye' para indicar que a senha está visível
        eyeIcon.classList.remove('fa-eye-slash');
        eyeIcon.classList.add('fa-eye');
    } else {
        // Se for 'text', mude para 'password' para ocultar a senha
        campoSenha.type = 'password';
        // Altere o ícone para 'fa-eye-slash' para indicar que a senha está oculta
        eyeIcon.classList.remove('fa-eye');
        eyeIcon.classList.add('fa-eye-slash');
    }
}
// Crie a instância de PIXI.Application
const app = new PIXI.Application({
    width: 800,  // Largura do canvas
    height: 1000, // Altura do canvas
    backgroundColor: 0xFFFFFF // Cor de fundo do canvas
});

// Adicione o canvas criado pelo PIXI.js ao contêiner
document.querySelector('#pixi-container').appendChild(app.view);

// Cria uma instância de PIXI.Graphics para desenhar as conexões
const connectionGraphics = new PIXI.Graphics();
app.stage.addChild(connectionGraphics);

// Função para criar uma partícula com forma de barra longa e cor aleatória
function createParticle() {
    const particle = new PIXI.Graphics();
    const isRed = Math.random() > 0.5; // 50% de chance de ser vermelho
    const color = isRed ? 0xFF0000 : 0x0000FF; // Cor vermelha ou azul
    particle.lineStyle(4, color); // Linha mais grossa

    const width = 10;
    const height = 50; // Aumenta a altura da barra

    // Desenha uma forma de barra longa
    particle.moveTo(-width / 2, -height / 2);
    particle.lineTo(width / 2, -height / 2);
    particle.lineTo(width / 2, height / 2);
    particle.lineTo(-width / 2, height / 2);
    particle.lineTo(-width / 2, -height / 2);
    
    particle.x = Math.random() * app.screen.width; // Posição inicial aleatória
    particle.y = Math.random() * app.screen.height;
    particle.vx = Math.random() * 2 - 1; // Velocidade reduzida
    particle.vy = Math.random() * 1 - 0.5;
    particle.color = color; // Armazena a cor da partícula
    return particle;
}

// Adiciona partículas ao estágio
const particles = [];
for (let i = 0; i < 20; i++) { // Menos partículas
    const particle = createParticle();
    app.stage.addChild(particle);
    particles.push(particle);
}

// Função para desenhar linhas entre partículas próximas e misturar cores
function drawConnections() {
    connectionGraphics.clear(); // Limpa as conexões anteriores
    connectionGraphics.lineStyle(2, 0x000000); // Linha preta para conexões

    particles.forEach((particle1, index1) => {
        particles.forEach((particle2, index2) => {
            if (index1 !== index2) {
                const dx = particle1.x - particle2.x;
                const dy = particle1.y - particle2.y;
                const distance = Math.sqrt(dx * dx + dy * dy);

                // Desenha linha se a distância entre partículas for pequena
                if (distance < 100) { // Distância ajustada
                    // Mistura de cores
                    const color1 = particle1.color;
                    const color2 = particle2.color;
                    const mixedColor = PIXI.utils.rgb2hex([
                        (PIXI.utils.hex2rgb(color1)[0] + PIXI.utils.hex2rgb(color2)[0]) / 2,
                        (PIXI.utils.hex2rgb(color1)[1] + PIXI.utils.hex2rgb(color2)[1]) / 2,
                        (PIXI.utils.hex2rgb(color1)[2] + PIXI.utils.hex2rgb(color2)[2]) / 2
                    ]);

                    connectionGraphics.lineStyle(2, mixedColor); // Linha com a cor misturada
                    connectionGraphics.moveTo(particle1.x, particle1.y);
                    connectionGraphics.lineTo(particle2.x, particle2.y);
                }
            }
        });
    });
}

// Função para atualizar a posição das partículas
function updateParticles() {
    particles.forEach(particle => {
        particle.x += particle.vx;
        particle.y += particle.vy;

        // Colisão com as bordas da tela
        if (particle.x < 0 || particle.x > app.screen.width) {
            particle.vx *= -1;
        }
        if (particle.y < 0 || particle.y > app.screen.height) {
            particle.vy *= -1;
        }
    });

    // Desenha as conexões entre partículas próximas
    drawConnections();
}

// Função de animação
function animate() {
    requestAnimationFrame(animate); // Solicita a próxima atualização de quadro
    updateParticles(); // Atualiza as partículas
    app.renderer.render(app.stage); // Renderiza o estágio
}

// Inicia a animação
animate();






document.addEventListener('DOMContentLoaded', function() {
    console.log('Script de login carregado');

    const loginButton = document.querySelector('.entrar');
    if (loginButton) {
        loginButton.addEventListener('click', function() {
            const user = document.getElementById('username').value;
            const password = document.getElementById('senha').value;

            if (!user || !password) {
                alert("Por favor, preencha os campos de usuário e senha.");
                return;
            }

            const loginData = {
                username: user,
                senha: password
            };

            fetch('http://localhost:8080/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(loginData)
            })
            .then(response => response.text())
            .then(message => {
                console.log("Mensagem recebida do backend:", message);
                alert(message);

                if (message.trim() === 'Login bem-sucedido!') {
                    window.location.href = 'Login.html';
                }
            })
            .catch(error => {
                alert('Erro ao processar o login: ' + error.message);
            });
        });
    } else {
        console.error('Botão de login não encontrado!');
    }
});
