document.addEventListener('DOMContentLoaded', function() {
    // Seleciona o botão de cadastro e adiciona um evento de clique que chama a função register()
    const registerButton = document.querySelector('#registerButton');
    if (registerButton) {
        registerButton.addEventListener('click', register);
    } else {
        console.error('Botão de cadastro não encontrado!');
    }
});

function register() {
    const user = document.querySelector('#registerUser').value;
    const password = document.querySelector('#registerPassword').value;
    const email = document.querySelector("#registeremail").value;
    console.log('Script carregado');

    if (!user || !password || !email) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const registerData = {
        users: user,
        senha: password,
        email: email
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
            console.log("Redirecionando para a página inicial...");
            const caixaCadastro = document.querySelector('#CaixaCadastro');
            const caixaLogin = document.querySelector('#CaixaLogin');
            if (caixaCadastro && caixaLogin) {
                caixaCadastro.style.display = 'none';
                caixaLogin.style.display = 'block';
                // Adiciona um pequeno atraso para garantir que a mudança de UI tenha efeito antes do redirecionamento
                setTimeout(() => {
                    window.location.href = 'PaginaInicial.html'; // Ajuste para o nome correto do arquivo
                }, 0,5); // Ajuste o atraso conforme necessário
            } else {
                console.error('Elementos de cadastro ou login não encontrados!');
            }
        }
    })
    .catch(error => {
        alert('Erro ao processar o cadastro: ' + error.message);
    });
}


// Crie a instância de PIXI.Application
const app = new PIXI.Application({
    width: 1000,  // Largura do canvas
    height: 2000, // Altura do canvas
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
