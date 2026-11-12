import jenkins.model.*
import hudson.security.*

def instance = Jenkins.get()

def user = hudson.model.User.getById("boinhaTI", false)

if (user == null) {
    println("Usuario nao encontrado")
    return
}

def realm = instance.getSecurityRealm()

if (!(realm instanceof HudsonPrivateSecurityRealm)) {
    println("O Jenkins nao utiliza autenticacao local")
    return
}

def details = realm.getUser("boinhaTI")

println("Usuario encontrado: " + user.getId())
println("Nome: " + user.getFullName())
