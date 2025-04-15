// Gestionnaire de transitions de page
class PageTransitions {
    constructor() {
        this.init();
    }
    
    init() {
        // Ajouter les styles nécessaires
        this.addStyles();
        
        // Intercepter les clics sur les liens
        document.addEventListener('click', (e) => {
            const link = e.target.closest('a');
            if (link && this.shouldHandleLink(link)) {
                e.preventDefault();
                this.navigateTo(link.href);
            }
        });
        
        // Gérer le retour arrière
        window.addEventListener('popstate', () => {
            this.handlePageTransition();
        });
    }
    
    addStyles() {
        const style = document.createElement('style');
        style.textContent = `
            .page-transition {
                opacity: 0;
                transform: translateY(20px);
                transition: opacity 0.3s ease-out, transform 0.3s ease-out;
            }
            
            .page-transition.active {
                opacity: 1;
                transform: translateY(0);
            }
            
            .page-loading {
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                height: 3px;
                background: linear-gradient(to right, #9333ea, #6366f1);
                transform: translateX(-100%);
                transition: transform 0.3s ease-out;
                z-index: 9999;
            }
            
            .page-loading.active {
                transform: translateX(0);
            }
        `;
        document.head.appendChild(style);
    }
    
    shouldHandleLink(link) {
        // Ne pas intercepter les liens externes ou les ancres
        return link.href.startsWith(window.location.origin) && 
               !link.href.includes('#') &&
               !link.hasAttribute('target') &&
               !link.hasAttribute('download');
    }
    
    async navigateTo(url) {
        // Afficher l'indicateur de chargement
        this.showLoading();
        
        try {
            // Charger la nouvelle page
            const response = await fetch(url);
            const html = await response.text();
            
            // Extraire le contenu principal
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const newContent = doc.querySelector('main, .main-content, #content');
            
            if (newContent) {
                // Mettre à jour l'URL
                window.history.pushState({}, '', url);
                
                // Animer la transition
                await this.animateTransition(newContent);
            } else {
                // Si pas de contenu principal, recharger la page
                window.location.href = url;
            }
        } catch (error) {
            console.error('Erreur lors de la navigation:', error);
            window.location.href = url;
        }
    }
    
    async animateTransition(newContent) {
        const currentContent = document.querySelector('main, .main-content, #content');
        
        if (currentContent) {
            // Ajouter la classe de transition
            currentContent.classList.add('page-transition');
            
            // Attendre la fin de l'animation de sortie
            await new Promise(resolve => setTimeout(resolve, 300));
            
            // Remplacer le contenu
            currentContent.innerHTML = newContent.innerHTML;
            
            // Ajouter la classe active pour l'animation d'entrée
            currentContent.classList.add('active');
            
            // Supprimer les classes après l'animation
            setTimeout(() => {
                currentContent.classList.remove('page-transition', 'active');
            }, 300);
        }
    }
    
    showLoading() {
        // Créer l'indicateur de chargement s'il n'existe pas
        let loader = document.querySelector('.page-loading');
        if (!loader) {
            loader = document.createElement('div');
            loader.className = 'page-loading';
            document.body.appendChild(loader);
        }
        
        // Afficher l'indicateur
        loader.classList.add('active');
        
        // Le cacher après un délai
        setTimeout(() => {
            loader.classList.remove('active');
        }, 1000);
    }
    
    handlePageTransition() {
        // Recharger la page actuelle
        window.location.reload();
    }
}

// Initialiser les transitions de page
document.addEventListener('DOMContentLoaded', () => {
    window.pageTransitions = new PageTransitions();
}); 