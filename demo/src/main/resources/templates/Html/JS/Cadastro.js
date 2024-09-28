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
            const endereco = document.getElementById('registerEndereco').value;
            const sexo = document.getElementById('registerSexo').value;

            if (!username || !password || !email || !telefone || !dataDeNasc || !endereco || !sexo) {
                alert("Por favor, preencha todos os campos.");
                return;
            }

            const registerData = {
                users: username,
                senha: password,
                email: email,
                telefone: telefone,
                datanasc: dataDeNasc,
                endereco: endereco,
                sexo: sexo
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

// Crie a instância de PIXI.Application
const app = new PIXI.Application({
    width: 2000,  // Largura do canvas
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


function drawConnections() {
    connectionGraphics.clear(); 
    connectionGraphics.lineStyle(2, 0x000000); 

    particles.forEach((particle1, index1) => {
        particles.forEach((particle2, index2) => {
            if (index1 !== index2) {
                const dx = particle1.x - particle2.x;
                const dy = particle1.y - particle2.y;
                const distance = Math.sqrt(dx * dx + dy * dy);

                
                if (distance < 100) { 
                    const color1 = particle1.color;
                    const color2 = particle2.color;
                    const mixedColor = PIXI.utils.rgb2hex([
                        (PIXI.utils.hex2rgb(color1)[0] + PIXI.utils.hex2rgb(color2)[0]) / 2,
                        (PIXI.utils.hex2rgb(color1)[1] + PIXI.utils.hex2rgb(color2)[1]) / 2,
                        (PIXI.utils.hex2rgb(color1)[2] + PIXI.utils.hex2rgb(color2)[2]) / 2
                    ]);

                    connectionGraphics.lineStyle(2, mixedColor); 
                    connectionGraphics.moveTo(particle1.x, particle1.y);
                    connectionGraphics.lineTo(particle2.x, particle2.y);
                }
            }
        });
    });
}


function updateParticles() {
    particles.forEach(particle => {
        particle.x += particle.vx;
        particle.y += particle.vy;

    
        if (particle.x < 0 || particle.x > app.screen.width) {
            particle.vx *= -1;
        }
        if (particle.y < 0 || particle.y > app.screen.height) {
            particle.vy *= -1;
        }
    });

    
    drawConnections();
}


function animate() {
    requestAnimationFrame(animate); 
    updateParticles();
    app.renderer.render(app.stage); 
}


animate();

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

