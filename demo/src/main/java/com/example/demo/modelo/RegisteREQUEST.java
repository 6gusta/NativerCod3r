package com.Native.coder.Modelo;
public class RegisterRequest {
    private Login login;
    private Email email;
    private Telefone telefone;
    private Endereco endereco;
    private Long mundoId;
    
    public RegisterRequest() {
    	
    }

    // Getters e Setters
    public Login getLogin() { return login; }
    public void setLogin(Login login) { this.login = login; }

    public Email getEmail() { return email; }
    public void setEmail(Email email) { this.email = email; }

    public Telefone getTelefone() { return telefone; }
    public void setTelefone(Telefone telefone) { this.telefone = telefone; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    
    public Long getMundoId() {
        return mundoId;
    }
    public void setMundoId(Long mundoId) {
        this.mundoId = mundoId;
    }
}
