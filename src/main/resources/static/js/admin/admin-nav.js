// Gestion de la navigation et du dropdown dans l'administration
document.addEventListener('DOMContentLoaded', function() {
    // Gestion du menu mobile
    const mobileMenuButton = document.querySelector('.mobile-menu-button');
    const mobileMenu = document.querySelector('.mobile-menu');
    const mobileMenuCloseButton = document.querySelector('.mobile-menu-close');

    if (mobileMenuButton && mobileMenu) {
        mobileMenuButton.addEventListener('click', () => {
            mobileMenu.classList.remove('hidden');
            mobileMenu.classList.add('active');
        });
    }

    if (mobileMenuCloseButton && mobileMenu) {
        mobileMenuCloseButton.addEventListener('click', () => {
            mobileMenu.classList.remove('active');
            setTimeout(() => {
                mobileMenu.classList.add('hidden');
            }, 300);
        });
    }

    // Gestion du dropdown du profil
    const profileDropdown = document.querySelector('.relative.group');
    const dropdownContent = profileDropdown?.querySelector('.absolute');

    if (profileDropdown && dropdownContent) {
        // Afficher le dropdown au hover sur mobile
        profileDropdown.addEventListener('touchstart', (e) => {
            e.preventDefault();
            dropdownContent.classList.toggle('invisible');
            dropdownContent.classList.toggle('opacity-0');
            dropdownContent.classList.toggle('scale-95');
        });

        // Masquer le dropdown quand on clique en dehors
        document.addEventListener('click', (e) => {
            if (!profileDropdown.contains(e.target)) {
                dropdownContent.classList.add('invisible', 'opacity-0', 'scale-95');
            }
        });
    }

    // Gestion du bouton de déconnexion
    const logoutButtons = document.querySelectorAll('.logout-button');
    logoutButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            if (confirm('Êtes-vous sûr de vouloir vous déconnecter ?')) {
                // Supprimer les données de session
                localStorage.removeItem('user');
                localStorage.removeItem('adminData');
                
                // Rediriger vers la page de connexion
                window.location.href = '../../index.html';
            }
        });
    });
}); 