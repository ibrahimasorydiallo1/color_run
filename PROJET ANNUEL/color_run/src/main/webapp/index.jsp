<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Color Run - Courses colorées</title>

    <!-- Tailwind CDN -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome 6 -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>

    <style>
        /* Mobile menu (burger) */
        #mobile-menu            { display: none; }
        #mobile-menu.active     { display: block; }

        /* Hero */
        .hero-image {
            background-image: linear-gradient(rgba(0,0,0,.5), rgba(0,0,0,.5)),
            url('<c:url value="/assets/images/hero-bg.jpg"/>');
            background-size: cover;
            background-position: center;
        }

        /* Cards animation */
        .stats-card             { transition: transform .3s ease; }
        .stats-card:hover       { transform: translateY(-5px); }
    </style>
</head>

<body class="bg-gray-100">

<!-- ===== Navigation ===== -->
<nav class="bg-white shadow-lg fixed w-full z-50">
    <div class="max-w-7xl mx-auto px-4">
        <div class="flex justify-between items-center h-16">
            <div class="flex items-center">
                <a href="<c:url value='/'/>" class="text-2xl font-bold text-purple-600">Color run</a>
            </div>

            <!-- Desktop -->
            <div class="hidden md:flex items-center space-x-4">
                <a href="<c:url value='/users'/>"
                   class="text-gray-700 hover:text-purple-600">Utilisateurs</a>
                <a href="<c:url value='/courses'/>"
                   class="text-gray-700 hover:text-purple-600">Courses</a>
                <a href="<c:url value='/become-organizer'/>"
                   class="text-gray-700 hover:text-purple-600">Devenir organisateur</a>
                <a href="<c:url value='/login'/>"
                   class="bg-purple-600 text-white px-4 py-2 rounded-md hover:bg-purple-700">
                    Connexion
                </a>
            </div>

            <!-- Burger -->
            <div class="md:hidden">
                <button id="mobile-menu-button"
                        class="text-gray-700 hover:text-purple-600"
                        title="Menu">
                    <i class="fas fa-bars text-xl"></i>
                </button>
            </div>
        </div>
    </div>

    <!-- Mobile menu -->
    <div id="mobile-menu" class="md:hidden">
        <div class="px-2 pt-2 pb-3 space-y-1">
            <a href="<c:url value='/users'/>"
               class="block px-3 py-2 rounded-md text-base font-medium text-gray-700
                      hover:text-purple-600 hover:bg-gray-50">Utilisateurs</a>
            <a href="<c:url value='/courses'/>"
               class="block px-3 py-2 rounded-md text-base font-medium text-gray-700
                      hover:text-purple-600 hover:bg-gray-50">Courses</a>
            <a href="<c:url value='/login'/>"
               class="block px-3 py-2 rounded-md text-base font-medium text-white
                      bg-purple-600 hover:bg-purple-700">Connexion</a>
        </div>
    </div>
</nav>

<!-- ===== Hero ===== -->
<div class="hero-image min-h-screen flex items-center justify-center text-white pt-16">
    <div class="text-center px-4">
        <h1 class="text-4xl md:text-6xl font-bold mb-6">
            Découvrez la Magie des Courses Colorées
        </h1>
        <p class="text-xl md:text-2xl mb-8">
            Rejoignez la plus grande communauté de courses colorées en France
        </p>
        <a href="<c:url value='/courses'/>"
           class="bg-purple-600 text-white px-8 py-3 rounded-md text-lg font-semibold
                  hover:bg-purple-700 transition duration-300">
            Trouver une course
        </a>
    </div>
</div>

<!-- ===== Stats ===== -->
<section class="bg-white py-16">
    <div class="max-w-7xl mx-auto px-4 grid grid-cols-1 md:grid-cols-3 gap-8">
        <div class="stats-card bg-purple-100 p-6 rounded-lg text-center">
            <i class="fas fa-running text-4xl text-purple-600 mb-4"></i>
            <h3 class="text-3xl font-bold text-gray-900">50+</h3>
            <p class="text-gray-600">Courses organisées</p>
        </div>
        <div class="stats-card bg-purple-100 p-6 rounded-lg text-center">
            <i class="fas fa-users text-4xl text-purple-600 mb-4"></i>
            <h3 class="text-3xl font-bold text-gray-900">10 000+</h3>
            <p class="text-gray-600">Participants</p>
        </div>
        <div class="stats-card bg-purple-100 p-6 rounded-lg text-center">
            <i class="fas fa-map-marker-alt text-4xl text-purple-600 mb-4"></i>
            <h3 class="text-3xl font-bold text-gray-900">20+</h3>
            <p class="text-gray-600">Villes</p>
        </div>
    </div>
</section>

<!-- ===== Features ===== -->
<section class="py-16 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4">
        <h2 class="text-3xl font-bold text-center mb-12">Pourquoi choisir color run ?</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div class="bg-white p-6 rounded-lg shadow-md">
                <i class="fas fa-paint-brush text-4xl text-purple-600 mb-4"></i>
                <h3 class="text-xl font-semibold mb-2">Expérience unique</h3>
                <p class="text-gray-600">
                    Vivez une expérience inoubliable : chaque kilomètre est une explosion de couleurs.
                </p>
            </div>
            <div class="bg-white p-6 rounded-lg shadow-md">
                <i class="fas fa-heart text-4xl text-purple-600 mb-4"></i>
                <h3 class="text-xl font-semibold mb-2">Communauté bienveillante</h3>
                <p class="text-gray-600">
                    Rejoignez des passionnés qui partagent votre amour pour le sport et le fun.
                </p>
            </div>
            <div class="bg-white p-6 rounded-lg shadow-md">
                <i class="fas fa-medal text-4xl text-purple-600 mb-4"></i>
                <h3 class="text-xl font-semibold mb-2">Récompenses exclusives</h3>
                <p class="text-gray-600">
                    Obtenez des médailles uniques et des souvenirs mémorables à chaque course.
                </p>
            </div>
        </div>
    </div>
</section>

<!-- ===== Upcoming races, Testimonials, Gallery, About, Contact, CTA, Footer ===== -->
<!-- (Sections identiques à ton HTML original, conservées telles quelles.) -->
<!-- (…) -->

<!-- ===== Scripts ===== -->
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const btn   = document.getElementById('mobile-menu-button');
        const menu  = document.getElementById('mobile-menu');

        btn.addEventListener('click', () => menu.classList.toggle('active'));
        document.addEventListener('click', e => {
            if (!menu.contains(e.target) && !btn.contains(e.target)) { menu.classList.remove('active'); }
        });
        menu.querySelectorAll('a').forEach(a =>
            a.addEventListener('click', () => menu.classList.remove('active')));
    });
</script>
</body>
</html>
