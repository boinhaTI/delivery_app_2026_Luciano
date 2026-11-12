import jenkins.model.Jenkins
import hudson.model.User
import hudson.security.HudsonPrivateSecurityRealm

def usuario = User.getById("boinhaTI", false)

if (usuario == null) {
    println("ERRO: usuario nao encontrado")
    return
}

def novaSenha = "20071950"

usuario.addProperty(
    HudsonPrivateSecurityRealm.Details.fromPlainPassword(novaSenha)
)

usuario.save()

println("SUCESSO: senha de boinhaTI redefinida!")
